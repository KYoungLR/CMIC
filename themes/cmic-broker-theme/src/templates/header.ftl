<header id="header" role="banner">
	<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
		<img alt="${logo_description}" height="56" src="${site_logo}" />
	</a>
	
	<#if has_navigation && is_setup_complete>
		<#include "${full_templates_path}/navigation.ftl" />
	</#if>

	<#if !is_signed_in>
		<a data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}" id="sign-in" rel="nofollow">${sign_in_text}</a>
	<#else>
		<span>User</span>
	</#if>
</header>

<#--<div id="heading">
	<div aria-level="1" class="site-title" role="heading">
		<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
			<img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" />
		</a>

		<#if show_site_name>
			<span class="site-name" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
				${site_name}
			</span>
		</#if>
	</div>
</div>

<#if !is_signed_in>
	<a data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}" id="sign-in" rel="nofollow">${sign_in_text}</a>
</#if>

<#if has_navigation && is_setup_complete>
	<#include "${full_templates_path}/navigation.ftl" />
</#if>-->