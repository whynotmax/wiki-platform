import React, { useEffect, useState } from 'react';
import { getAllEntries } from '../api/api';

// Sidebar component: Only renders the "Pages" header and a list of all pages.
// Sub-pages are no longer displayed here; they should be rendered as markdown `##` inside the page content.
export default function Sidebar({ onSelect, activeId }) {
    const [pages, setPages] = useState([]);

    useEffect(() => {
        getAllEntries().then(setPages);
    }, []);

    return (
        <nav className="sidebar">
            <div className="sidebar-group">
                <div className="sidebar-section">Pages</div>
                <ul>
                    {pages.map(page => (
                        <li
                            key={page.id}
                            className={activeId === page.id ? "active" : ""}
                            onClick={() => onSelect(page.id)}
                        >
                            {page.title}
                        </li>
                    ))}
                </ul>
            </div>
        </nav>
    );
}