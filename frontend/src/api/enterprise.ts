import { apiClient } from './client';

export interface LegalEntity {
  id: string;
  code: string;
  name: string;
  legalName?: string;
  registrationNumber?: string;
  taxIdentifier?: string;
  countryCode: string;
  baseCurrencyCode: string;
  status: string;
}

export interface Branch {
  id: string;
  legalEntityId: string;
  code: string;
  name: string;
  branchType?: string;
  countryCode: string;
  city?: string;
  status: string;
}

export interface Currency {
  code: string;
  name: string;
  numericCode?: string;
  minorUnits: number;
  status: string;
}

export interface BusinessCalendar {
  id: string;
  code: string;
  name: string;
  countryCode?: string;
  currencyCode?: string;
  description?: string;
  status: string;
}

export interface CalendarHoliday {
  id: string;
  calendarId: string;
  holidayDate: string;
  name: string;
  holidayType?: string;
}

export const enterpriseApi = {
  // Legal Entities
  getLegalEntities: () => apiClient('/enterprise/legal-entities').then((res: any) => res.json()),
  getLegalEntity: (id: string) => apiClient(`/enterprise/legal-entities/${id}`).then((res: any) => res.json()),
  createLegalEntity: (data: any) => apiClient('/enterprise/legal-entities', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),
  updateLegalEntity: (id: string, data: any) => apiClient(`/enterprise/legal-entities/${id}`, {
    method: 'PUT',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Branches
  getBranches: (legalEntityId: string) => apiClient(`/enterprise/legal-entities/${legalEntityId}/branches`).then((res: any) => res.json()),
  createBranch: (legalEntityId: string, data: any) => apiClient(`/enterprise/legal-entities/${legalEntityId}/branches`, {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Currencies
  getCurrencies: () => apiClient('/enterprise/currencies').then((res: any) => res.json()),

  // Calendars
  getCalendars: () => apiClient('/enterprise/calendars').then((res: any) => res.json()),
  createCalendar: (data: any) => apiClient('/enterprise/calendars', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),
  getHolidays: (calendarId: string) => apiClient(`/enterprise/calendars/${calendarId}/holidays`).then((res: any) => res.json()),
  addHoliday: (calendarId: string, data: any) => apiClient(`/enterprise/calendars/${calendarId}/holidays`, {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),
};
