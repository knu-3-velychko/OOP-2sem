export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
}

export function getEmptyUser(): User {
    const user: User = {
        id: null,
        email: null,
        name: null,
        surname: null
    };

    return user;
}

export function getUser(id: number, email: string, name: string, surname: string) {
    const user: User = {
        id: id,
        email: email,
        name: name,
        surname: surname
    };

    return user;
}