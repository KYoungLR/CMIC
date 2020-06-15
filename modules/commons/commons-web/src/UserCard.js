import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import UserAvatar from './UserAvatar';

const UserCard = (props) => {
	const userCardClassName = getCN(
		'user-card',
		props.className
	);

	return (
		<div className={userCardClassName}>
			{props.image &&
				<div className="user-card-image">
					<UserAvatar image={props.image} size="xxl" elevation="6" />
				</div>
			}
			<div className="user-card-body">
				<h4 className="user-card-title">{props.name}</h4>
				{props.title &&
					<div className="user-card-subtitle">{props.title}</div>
				}
				{props.email &&
					<div className="user-card-text"><a href={`mailto:${props.email}`}>{props.email}</a></div>
				}
				{props.phone &&
					<div className="user-card-text"><a href={`tel:${props.phone}`}>{props.phone}</a></div>
				}
			</div>
		</div>
	)
};

export default UserCard;
