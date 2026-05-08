import { apiClient } from './client';

export interface Account {
  id: string;
  accountNumber: string;
  customerId: string;
  partyId: string;
  legalEntityId: string;
  branchId?: string;
  productId: string;
  currencyCode: string;
  accountType: string;
  accountName: string;
  accountStatus: string;
  lifecycleStatus: string;
  openingDate: string;
  closingDate?: string;
  overdraftLimit: number;
  minimumBalance: number;
  createdAt: string;
  updatedAt?: string;
}

export interface AccountBalance {
  id: string;
  accountId: string;
  currencyCode: string;
  ledgerBalance: number;
  availableBalance: number;
  blockedBalance: number;
  unclearedBalance: number;
  overdraftAvailable: number;
  lastUpdatedAt: string;
}

export interface AccountBlock {
  id: string;
  accountId: string;
  blockReference: string;
  blockAmount: number;
  blockReason: string;
  status: string;
  createdAt: string;
  releasedAt?: string;
  createdBy: string;
  releasedBy?: string;
}

export interface AccountLifecycleHistory {
  id: string;
  accountId: string;
  action: string;
  previousStatus?: string;
  newStatus: string;
  reason?: string;
  actedBy: string;
  actedAt: string;
}

export const accountApi = {
  // Accounts
  getAccounts: () => apiClient('/accounts').then((res: any) => res.json()),
  getAccount: (id: string) => apiClient(`/accounts/${id}`).then((res: any) => res.json()),
  getAccountByNumber: (num: string) => apiClient(`/accounts/by-number/${num}`).then((res: any) => res.json()),
  openAccount: (data: any) => apiClient('/accounts', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Balance
  getBalance: (id: string) => apiClient(`/accounts/${id}/balance`).then((res: any) => res.json()),

  // Lifecycle
  freezeAccount: (id: string, reason: string) => apiClient(`/accounts/${id}/freeze`, {
    method: 'POST',
    body: JSON.stringify({ reason }),
  }),
  unfreezeAccount: (id: string, reason: string) => apiClient(`/accounts/${id}/unfreeze`, {
    method: 'POST',
    body: JSON.stringify({ reason }),
  }),
  closeAccount: (id: string, reason: string) => apiClient(`/accounts/${id}/close`, {
    method: 'POST',
    body: JSON.stringify({ reason }),
  }),
  getHistory: (id: string) => apiClient(`/accounts/${id}/lifecycle-history`).then((res: any) => res.json()),

  // Blocks
  getBlocks: (id: string) => apiClient(`/accounts/${id}/blocks`).then((res: any) => res.json()),
  placeBlock: (id: string, data: any) => apiClient(`/accounts/${id}/blocks`, {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),
  releaseBlock: (blockId: string) => apiClient(`/accounts/blocks/${blockId}/release`, {
    method: 'POST',
  }),
};
