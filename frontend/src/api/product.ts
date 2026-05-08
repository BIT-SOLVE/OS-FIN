import { apiClient } from './client';

export interface ProductType {
  id: string;
  code: string;
  name: string;
  description?: string;
  productFamily: string;
  status: string;
}

export interface Product {
  id: string;
  productCode: string;
  productName: string;
  productTypeId: string;
  legalEntityId?: string;
  currencyCode?: string;
  productFamily: string;
  productCategory?: string;
  lifecycleStatus: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  description?: string;
}

export interface InterestRule {
  id: string;
  productId: string;
  ruleCode: string;
  interestType: string;
  rateType: string;
  fixedRate?: number;
  indexCode?: string;
  spreadRate?: number;
  calculationBasis?: string;
  compoundingFrequency?: string;
  accrualFrequency?: string;
  status: string;
}

export const productApi = {
  // Product Types
  getProductTypes: () => apiClient('/products/types').then((res: any) => res.json()),
  createProductType: (data: any) => apiClient('/products/types', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Products
  getProducts: () => apiClient('/products').then((res: any) => res.json()),
  getProduct: (id: string) => apiClient(`/products/${id}`).then((res: any) => res.json()),
  createProduct: (data: any) => apiClient('/products', {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),
  updateProduct: (id: string, data: any) => apiClient(`/products/${id}`, {
    method: 'PUT',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Rules
  getInterestRules: (productId: string) => apiClient(`/products/${productId}/interest-rules`).then((res: any) => res.json()),
  addInterestRule: (productId: string, data: any) => apiClient(`/products/${productId}/interest-rules`, {
    method: 'POST',
    body: JSON.stringify(data),
  }).then((res: any) => res.json()),

  // Lifecycle
  submitProduct: (id: string, comment: string) => apiClient(`/products/${id}/submit`, {
    method: 'POST',
    body: JSON.stringify({ comment }),
  }),
  approveProduct: (id: string, comment: string) => apiClient(`/products/${id}/approve`, {
    method: 'POST',
    body: JSON.stringify({ comment }),
  }),
  rejectProduct: (id: string, comment: string) => apiClient(`/products/${id}/reject`, {
    method: 'POST',
    body: JSON.stringify({ comment }),
  }),
  getApprovalHistory: (id: string) => apiClient(`/products/${id}/approval-history`).then((res: any) => res.json()),
};
