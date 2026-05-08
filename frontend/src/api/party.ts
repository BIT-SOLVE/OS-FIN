import { apiClient } from './client';

export interface Party {
  id: string;
  partyNumber: string;
  partyType: string;
  displayName: string;
  legalName?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  dateOfBirth?: string;
  incorporationDate?: string;
  countryOfResidence?: string;
  countryOfIncorporation?: string;
  taxIdentifier?: string;
  email?: string;
  phoneNumber?: string;
  status: string;
  createdAt: string;
  updatedAt?: string;
}

export interface Customer {
  id: string;
  customerNumber: string;
  partyId: string;
  legalEntityId: string;
  branchId?: string;
  customerType: string;
  customerSegment?: string;
  riskRating: string;
  onboardingStatus: string;
  customerStatus: string;
  relationshipManager?: string;
  openedAt?: string;
  closedAt?: string;
  createdAt: string;
  updatedAt?: string;
}

export interface KycProfile {
  id: string;
  customerId: string;
  kycStatus: string;
  riskScore?: number;
  pepFlag: boolean;
  sanctionsScreeningStatus?: string;
  adverseMediaStatus?: string;
  sourceOfFunds?: string;
  sourceOfWealth?: string;
  expectedMonthlyTurnover?: number;
  expectedMonthlyTransactionCount?: number;
  lastReviewDate?: string;
  nextReviewDate?: string;
  approvedBy?: string;
  approvedAt?: string;
  rejectionReason?: string;
}

export const partyApi = {
  // Parties
  getParties: () => apiClient('/party/parties').then((res: any) => res.json()),
  getParty: (id: string) => apiClient(`/party/parties/${id}`).then((res: any) => res.json()),
  createParty: (data: any) => apiClient('/party/parties', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Customers
  getCustomers: () => apiClient('/party/customers').then((res: any) => res.json()),
  getCustomer: (id: string) => apiClient(`/party/customers/${id}`).then((res: any) => res.json()),
  onboardCustomer: (data: any) => apiClient('/party/customers/onboard', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // KYC
  getKycProfile: (customerId: string) => apiClient(`/party/customers/${customerId}/kyc`).then((res: any) => res.json()),
  approveKyc: (customerId: string) => apiClient(`/party/customers/${customerId}/kyc/approve`, {
    method: 'POST',
  }).then((res: any) => res.json()),
  rejectKyc: (customerId: string, reason: string) => apiClient(`/party/customers/${customerId}/kyc/reject?reason=${encodeURIComponent(reason)}`, {
    method: 'POST',
  }).then((res: any) => res.json()),
};
