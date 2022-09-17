import React from 'react';

const HelloList = ({ hello }) => {
    return (
        <div>
            {hello&&hello.result.data.map(user => {
                return (<div key={user.id}>
                    {user.name}
                </div>)
            })}
        </div>
    );
};

export default HelloList;