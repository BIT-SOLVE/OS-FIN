import { getToken, updateToken } from '../auth/keycloak';

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

export const apiClient = async (endpoint: string, options: RequestInit = {}) => {
  const token = getToken();

  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  } as Record<string, string>;

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(`${apiBaseUrl}${endpoint}`, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    // Attempt to refresh token or redirect to login
    return new Promise((resolve, reject) => {
      updateToken(async () => {
        try {
          const newToken = getToken();
          headers['Authorization'] = `Bearer ${newToken}`;
          const retryResponse = await fetch(`${apiBaseUrl}${endpoint}`, {
            ...options,
            headers,
          });
          resolve(retryResponse);
        } catch (error) {
          reject(error);
        }
      });
    });
  }

  return response as Response;
};
