import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';

const UserAvatar = (props) => {
	const size = props.size || 'lg';
	const variations = ['blue', 'green', 'orange'];
	const color = props.index !== undefined ? variations[props.index % variations.length] : 'light';
	const avatarClassName = getCN(
		[`sticker sticker-circle sticker-${color} sticker-${size}`],
		{
			[`elevation-${props.elevation}`]: props.elevation
		},
		props.className
	);

	return (
		<span className={avatarClassName} onClick={props.onClick}>
			<span className="sticker-overlay">
				{props.image ? (
					<img className="sticker-img" src={props.image} />
				) : (
					(props.firstName && props.lastName) ? (
						`${props.firstName.substring(0, 1)}${props.lastName.substring(0, 1)}`
					) : (
						<ClayIcon symbol="user" />
					)
				)}
			</span>
		</span>
	)
};

export default UserAvatar;
