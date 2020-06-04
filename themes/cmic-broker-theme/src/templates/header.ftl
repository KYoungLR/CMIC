<header id="header" role="banner">
	<#if has_navigation && is_setup_complete>
		<button class="btn btn-sm link-action d-none d-md-block d-lg-none js-navigation-toggler">
			<@liferay.language key="menu" />
		</button>
	</#if>

	<a class="${logo_css_class}" href="${site_default_url}">
		<svg class="logo-icon">
			<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#logo" />
		</svg>
	</a>
	
	<#if has_navigation && is_setup_complete>
		<#include "${full_templates_path}/navigation.ftl" />
	</#if>

	<div class="nav-user-bar">
		<#if !is_signed_in>
			<a data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}" class="text-center" rel="nofollow" title="${sign_in_text}">
				<svg class="lexicon-icon lexicon-icon-sign-in">
					<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#sign-in" />
				</svg>
			</a>
		<#else>
			<div class="user-notification">
				<a href="javascript:;">
					<svg class="lexicon-icon lexicon-icon-bell-on">
						<use xlink:href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#bell-on" />
					</svg>
					<#--<span class="badge badge-danger">
						<span class="badge-item badge-item-expand">${notifications_count!0}</span>
					</span>-->
				</a>
			</div>
			<div class="user-personal-bar">
				<@liferay.user_personal_bar />
			</span>
		</#if>
	</div>
</header>
