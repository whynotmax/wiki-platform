const API_BASE = 'http://localhost:8080/wiki';

export async function getRootEntries() {
    return fetch(`${API_BASE}/root`).then(res => res.json());
}

export async function getSubEntries(id) {
    return fetch(`${API_BASE}/${id}/sub`).then(res => res.json());
}

export async function getEntryById(id) {
    return fetch(`${API_BASE}/${id}`).then(res => res.json());
}

export async function getAllEntries() {
    return fetch(API_BASE).then(res => res.json());
}