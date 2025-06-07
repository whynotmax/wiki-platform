import React, { useEffect, useState } from 'react';
import { getRootEntries, getSubEntries } from '../api/api';

export default function Sidebar({ onSelect, activeId }) {
    const [sections, setSections] = useState([]);

    useEffect(() => {
        getRootEntries().then(async (entries) => {
            // For each root entry, fetch its subentries
            const sectionsWithSubs = await Promise.all(entries.map(async (entry) => {
                const subs = await getSubEntries(entry.id);
                return { ...entry, subs };
            }));
            setSections(sectionsWithSubs);
        });
    }, []);

    return (
        <nav className="sidebar">
            <div className="sidebar-group">
                {sections.map(section => (
                    <div key={section.id}>
                        <div className="sidebar-section">{section.title}</div>
                        <ul>
                            <li
                                className={activeId === section.id ? "active" : ""}
                                onClick={() => onSelect(section.id)}
                            >
                                {section.title}
                            </li>
                            {section.subs.map(sub => (
                                <li
                                    key={sub.id}
                                    className={"sidebar-subitem" + (activeId === sub.id ? " active" : "")}
                                    onClick={() => onSelect(sub.id)}
                                    style={{ marginLeft: "16px", fontWeight: 400 }}
                                >
                                    {sub.title}
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
            <div className="sidebar-group">
                <div className="sidebar-section">Pages</div>
                <PagesList onSelect={onSelect} activeId={activeId} />
            </div>
        </nav>
    );
}

import { getAllEntries } from '../api/api';

export function PagesList({ onSelect, activeId }) {
    const [pages, setPages] = useState([]);
    useEffect(() => {
        getAllEntries().then(setPages);
    }, []);
    return (
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
    );
}