import React, { useEffect, useState } from 'react';
import { getAllEntries, getSubEntries } from '../api/api';

// Sidebar: Only renders the "Pages" header and a list of top-level pages (no subpages).
export default function Sidebar({ onSelect, activeId }) {
    const [pages, setPages] = useState([]);

    useEffect(() => {
        // Fetch all entries, then filter to only root pages (no parent)
        getAllEntries().then(async (allPages) => {
            // Option 1: If your page objects have a 'parentId' or similar:
            // const rootPages = allPages.filter(page => !page.parentId);
            // setPages(rootPages);

            // Option 2: If you have a way to get only root entries, use that instead:
            // Uncomment and use this if available:
            // getRootEntries().then(setPages);

            // If you don't have a parentId field, just use allPages for now:
            setPages(allPages);
        });
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