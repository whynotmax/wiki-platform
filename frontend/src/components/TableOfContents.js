import React, { useEffect, useState } from 'react';
import { getSubEntries } from '../api/api';

// TableOfContents: Only shows subpages for the current page (if any), as "On this page"
export default function TableOfContents({ pageId, onSelect }) {
    const [subs, setSubs] = useState([]);

    useEffect(() => {
        if (pageId) {
            getSubEntries(pageId).then(setSubs);
        } else {
            setSubs([]);
        }
    }, [pageId]);

    if (!subs.length) return null; // Hide TOC if there are no subsections

    return (
        <nav className="toc">
            <div className="toc-title" style={{ display: 'flex', alignItems: 'center', marginBottom: 8 }}>
                <span style={{ fontSize: '1.2em', marginRight: 6 }}>≡</span>
                <span>On this page</span>
            </div>
            <ul style={{ listStyle: 'none', paddingLeft: 10, margin: 0 }}>
                {subs.map(sub => (
                    <li key={sub.id} style={{ marginBottom: 4 }}>
                        <a
                            href={`#${sub.id}`}
                            onClick={e => {
                                e.preventDefault();
                                onSelect(sub.id);
                            }}
                            style={{
                                cursor: 'pointer',
                                color: '#48a0fa',
                                textDecoration: 'none'
                            }}
                        >
                            {sub.title}
                        </a>
                    </li>
                ))}
            </ul>
        </nav>
    );
}