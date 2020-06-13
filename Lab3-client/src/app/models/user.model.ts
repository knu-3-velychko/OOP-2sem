export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
}

export function getEmptyUser(): User {
    return {
        id: null,
        email: null,
        name: null,
        surname: null
    };
}

export function getUser(id: number, email: string, name: string, surname: string) {
    return {
        id,
        email,
        name,
        surname
    };
}
