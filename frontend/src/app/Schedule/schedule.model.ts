export class ScheduleTableTo {
    period: string;
    monday: ScheduleTo;
    tuesday: ScheduleTo;
    wednesday: ScheduleTo;
    thursday: ScheduleTo;
    friday: ScheduleTo;
    saturday: ScheduleTo;
    sunday: ScheduleTo;
}

export class ScheduleTo {
    id?: string;
    date?: Date;
    user?: User;
    priority?: number;
    status?: string;
    classification?: string;
    content?: string;
    address?: string;
    rate?: Rate;
}

export class User {
    id: number;
    name: string;
}

export class Rate {
    veryGood: number;
    good: number;
    like: number;
}