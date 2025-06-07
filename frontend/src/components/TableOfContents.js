import React, { useEffect, useState } from 'react';
import { getSubEntries } from '../api/api';

export default function TableOfContents({ pageId, onSelect }) {
    const [subs, setSubs] = useState([]);

    useEffect(() => {
        if (pageId) {
            getSubEntries(pageId).then(setSubs);
        }
    }, [pageId]);

    return (
        <aside className="toc">
            <div><b>On this page</b></div>
            <ul>
                {subs.map(sub => (
                    <li key={sub.id} onClick={() => onSelect(sub.id)} style={{ cursor: 'pointer', color: '#48a0fa' }}>
                        {sub.title}
                    </li>
                ))}
            </ul>
        </aside>
    );
}