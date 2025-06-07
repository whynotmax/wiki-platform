import React, { useState } from 'react';
import Sidebar from './Sidebar';
import PageContent from './PageContent';
import TableOfContents from './TableOfContents';

export default function Layout() {
    const [pageId, setPageId] = useState(null);

    return (
        <div className="layout">
            <Sidebar onSelect={setPageId} activeId={pageId} />
            <PageContent pageId={pageId} />
            <TableOfContents pageId={pageId} onSelect={setPageId} />
        </div>
    );
}