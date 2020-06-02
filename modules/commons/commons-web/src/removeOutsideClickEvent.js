import React, {useEffect} from 'react';

const removeOutsideClickEvent = (ref) => {
    useEffect(() => {
        const handleClickOutside = event => {
            if (ref.current && !ref.current.contains(event.target)) {
                event.preventDefault();
                event.stopPropagation();
            }
        };

        document.addEventListener('click', handleClickOutside);
        
        return () => {
            document.removeEventListener('click', handleClickOutside)
        };
    }, [ref]);
}

export {removeOutsideClickEvent}