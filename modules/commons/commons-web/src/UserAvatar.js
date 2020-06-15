import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';

const UserAvatar = (props) => {
	const size = props.size || 'lg';
	const avatarClassName = getCN(
		[`sticker sticker-circle sticker-light sticker-${size}`],
		{
			[`elevation-${props.elevation}`]: props.elevation
		},
		props.className
	);

	return (
		<span className={avatarClassName}>
			<span className="sticker-overlay">
				{props.image ? (
					<img className="sticker-img" src={props.image} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</span>
		</span>
	)
};

export default UserAvatar;
