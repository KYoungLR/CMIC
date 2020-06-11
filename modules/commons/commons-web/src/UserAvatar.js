import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';

const UserAvatar = ({className, image}) => {
	const avatarClassName = getCN(
		className,
		'sticker sticker-circle sticker-light sticker-lg'
	);

	return (
		<span className={avatarClassName}>
			<span className="sticker-overlay">
				{image ? (
					<img className="sticker-img" src={image} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</span>
		</span>
	)
};

export default UserAvatar;
