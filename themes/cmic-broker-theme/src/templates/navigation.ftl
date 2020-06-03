<nav class="${nav_css_class}" id="navigation" role="navigation">
	<ul class="nav" aria-label="<@liferay.language key="site-pages" />" role="menubar">
		<#list nav_items as nav_item>
			<#assign
				nav_item_attr_has_popup = ""
				nav_item_css_class = "nav-item"
				nav_item_layout = nav_item.getLayout()
				nav_link_css_class = "nav-link"
				nav_item_icon = "arrow-right"
			/>

			<#if nav_item.isSelected()>
				<#assign
					nav_item_attr_has_popup = "aria-haspopup='true'"
					nav_item_css_class = nav_item_css_class + " selected"
					nav_link_css_class = nav_link_css_class + " active"
				/>
			</#if>

			<#if nav_item_icon_hash[nav_item.getName()?lower_case]??>
				<#assign nav_item_icon = nav_item_icon_hash[nav_item.getName()?lower_case] />
			</#if>

			<li class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" role="presentation">
				<a class="${nav_link_css_class}" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
					<span class="layout-icon">
						<svg class="nav-item-icon">
							<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#${nav_item_icon}"></use>
						</svg>
					</span>
					<span class="layout-name">${nav_item.getName()}</span>
				</a>

				<#--<#if nav_item.hasChildren()>
					<ul class="child-menu" role="menu">
						<#list nav_item.getChildren() as nav_child>
							<#assign
								nav_child_css_class = ""
							/>

							<#if nav_item.isSelected()>
								<#assign
									nav_child_css_class = "selected"
								/>
							</#if>

							<li class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" role="presentation">
								<a aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">${nav_child.getName()}</a>
							</li>
						</#list>
					</ul>
				</#if>-->
			</li>
		</#list>
	</ul>
</nav>