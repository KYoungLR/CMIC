<#if layout.getFriendlyURL(locale) == "/dashboard">
	<#assign lg = "12" />
<#else>
	<#assign lg = "4" />
</#if>

<div class="card">
	<div class="card-header">
		<p class="card-title" title="Contact Us">
			<span class="text-truncate-inline">
				<span class="text-truncate">Contact Us</span>
			</span>
		</p>
	</div>

	<div class="card-body">
		<@liferay_aui.row>
<@liferay_aui.col
	lg="${lg}"
	md="6"
>
				<div class="align-items-center user-card">
					<div class="user-card-image">
						<span class="elevation-6 sticker sticker-blue sticker-circle sticker-xxl">
							<span class="sticker-overlay">
								<svg class="lexicon-icon lexicon-icon-envelope-closed">
									<use xlink:href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#envelope-closed" />
								</svg>
							</span>
						</span>
					</div>

					<div class="user-card-body">
						<h4 class="user-card-title">Claim Service Coordinator</h4>

						<div class="user-card-text">
							<a href="mailto:brokerclaimservices@churchmutual.com">brokerclaimservices@churchmutual.com</a>
						</div>
					</div>
				</div>
			</@>
<@liferay_aui.col
	lg="${lg}"
	md="6"
>
				<div class="align-items-center user-card">
					<div class="user-card-image">
						<span class="elevation-6 sticker sticker-circle sticker-green sticker-xxl">
							<span class="sticker-overlay">
								<svg class="lexicon-icon lexicon-icon-envelope-closed">
									<use xlink:href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#envelope-closed" />
								</svg>
							</span>
						</span>
					</div>

					<div class="user-card-body">
						<h4 class="user-card-title">Billing</h4>

						<div class="user-card-text">
							<a href="mailto:brokerbillingandcommissionsupport@churchmutual.com">brokerbillingandcommissionsupport@churchmutual.com</a>
						</div>
					</div>
				</div>
			</@>
<@liferay_aui.col
	lg="${lg}"
	md="6"
>
				<div class="align-items-center user-card">
					<div class="user-card-image">
						<span class="elevation-6 sticker sticker-circle sticker-orange sticker-xxl">
							<span class="sticker-overlay">
								<svg class="lexicon-icon lexicon-icon-envelope-closed">
									<use xlink:href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#envelope-closed" />
								</svg>
							</span>
						</span>
					</div>

					<div class="user-card-body">
						<h4 class="user-card-title">Risk Control Service Coordinator</h4>

						<div class="user-card-text">
							<a href="mailto:brokerriskservices@churchmutual.com">brokerriskservices@churchmutual.com</a>
						</div>
					</div>
				</div>
			</@>
		</@>
	</div>
</div>