import React, { useEffect, useState } from 'react';
import { getEntryById } from '../api/api';
import TableOfContents from './TableOfContents';

// Renders the main page, and TableOfContents for subpages of this page only.
export default function PageContent({ pageId, onSelectSubsection }) {
    const [entry, setEntry] = useState(null);

    useEffect(() => {
        if (pageId) {
            getEntryById(pageId).then(setEntry);
        }
    }, [pageId]);

    if (!entry) return <div>Loading...</div>;

    return (
        <main className="main-content">
            <div className="page-category">{entry.category || ""}</div>
            <h1>{entry.title}</h1>
            <TableOfContents pageId={pageId} onSelect={onSelectSubsection} />
            <div className="page-content">
                <p>{entry.content}</p>
            </div>
        </main>
    );
}