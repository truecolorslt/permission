package com.lt.permission.shiro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.model.Function;
import com.lt.permission.model.Role;
import com.lt.permission.service.IFunctionService;
import com.lt.permission.service.IRoleService;
import com.lt.permission.shiro.service.ShiroManager;

/**
 * 动态加载权限 Service
 * 
 * @author lantian
 * 
 */
public class ShiroManagerImpl implements ShiroManager {
	private final Logger logger = Logger.getLogger(getClass());

	// 注意/r/n前不能有空格
	private static final String CRLF = "\r\n";

	@Autowired
	private IFunctionService functionService;
	@Autowired
	private IRoleService roleService;

	@Resource
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	@Override
	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer();
		sb.append(getFixedAuthRule());// 固定权限，采用读取配置文件
		return sb.toString();
	}

	/**
	 * 从配额文件获取固定权限验证规则串
	 */
	private String getFixedAuthRule() {
		// 从数据库中获取菜单信息
		List<Function> functionList = functionService.getFunctionHasUrl();

		StringBuffer sb = new StringBuffer();
		// 登录成功主页
		sb.append("/main = authc,login").append(CRLF);
		sb.append("/function/findUserFunctionTrees = authc,login").append(CRLF);
		// 对静态资源设置匿名访问
		sb.append("/static/* = anon ").append(CRLF);
		sb.append("/login.jsp = anon ").append(CRLF);
		sb.append("/logout = logout ").append(CRLF);

		if (functionList != null && functionList.size() > 0) {
			// sb.append("/function/* = authc,roles[ROLE_ADMIN_SUPER] ").append(CRLF);
			// sb.append("/dict/* = authc,roles[ROLE_ADMIN_SUPER] ").append(CRLF);
			// sb.append("/user/* = authc,roles[ROLE_ADMIN_SUPER] ").append(CRLF);
			// sb.append("/role/* = authc,roles[ROLE_ADMIN_SUPER] ").append(CRLF);
			// sb.append("/log/* = authc,roles[ROLE_ADMIN_SUPER] ").append(CRLF);
			for (Function f : functionList) {
				// 根据fid获取角色code
				List<Role> roleList = roleService.getRolesByFid(f.getFid());
				String roleStr = "ROLE_ADMIN_SUPER";
				if (roleList != null && roleList.size() > 0) {
					for (Role r : roleList) {
						if (!"ROLE_ADMIN_SUPER".equals(r.getRcode())) {
							roleStr += "," + r.getRcode();
						}
					}
				}
				String furl = f.getFurl();
				if (furl.indexOf("/") != -1) {
					furl = furl.substring(0, furl.indexOf("/"));
				}
				sb.append(
						"/" + furl + "/* = authc,login,roleOr[\"" + roleStr
								+ "\"] ").append(CRLF);
			}
		}

		logger.info("================权限验证规则串 start================:");
		logger.info(sb.toString());
		logger.info("================权限验证规则串 end================:");
		return sb.toString();

	}

	// 此方法加同步锁
	@Override
	public synchronized void reCreateFilterChains() {
		// ShiroFilterFactoryBean shiroFilterFactoryBean =
		// (ShiroFilterFactoryBean)
		// SpringContextUtil.getBean("shiroFilterFactoryBean");
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
					.getObject();
		} catch (Exception e) {
			throw new RuntimeException(
					"get ShiroFilter from shiroFilterFactoryBean error!");
		}

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
				.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
				.getFilterChainManager();

		// 清空老的权限控制
		manager.getFilterChains().clear();

		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean
				.setFilterChainDefinitions(loadFilterChainDefinitions());
		// 重新构建生成
		Map<String, String> chains = shiroFilterFactoryBean
				.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}

	}

	public void setShiroFilterFactoryBean(
			ShiroFilterFactoryBean shiroFilterFactoryBean) {
		this.shiroFilterFactoryBean = shiroFilterFactoryBean;
	}

}
