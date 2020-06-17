import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';

const UserAvatar = (props) => {
	const size = props.size || 'lg';
	const initials = `${props.firstName.substring(0, 1)}${props.lastName.substring(0, 1)}`;
	const variations = ['blue', 'green', 'orange'];
	const color = variations[props.index % variations.length];
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
					initials
				)}
			</span>
		</span>
	)
};

export default UserAvatar;
