import React, { useEffect, useState } from 'react';
import { getEntryById, getSubEntries } from '../api/api';

export default function PageContent({ pageId }) {
    const [entry, setEntry] = useState(null);
    const [subs, setSubs] = useState([]);

    useEffect(() => {
        if (pageId) {
            getEntryById(pageId).then(setEntry);
            getSubEntries(pageId).then(setSubs);
        }
    }, [pageId]);

    if (!entry) return <div>Loading...</div>;

    return (
        <main className="main-content">
            <div className="page-category">{entry.category || ""}</div>
            <h1>{entry.title}</h1>
            <p>{entry.content}</p>
            {/* Optionally render subsections */}
            {subs.length > 0 && (
                <section>
                    <h2>Subsections</h2>
                    <ul>
                        {subs.map(sub => <li key={sub.id}>{sub.title}</li>)}
                    </ul>
                </section>
            )}
        </main>
    );
}