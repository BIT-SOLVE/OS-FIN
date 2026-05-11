import React, { useEffect, useState } from 'react';
import { getUsername, doLogin, doLogout, isAuthenticated } from '../auth/keycloak';
import { apiClient } from '../api/client';

const UserStatus: React.FC = () => {
  const [backendUser, setBackendUser] = useState<any>(null);
  const authenticated = isAuthenticated();

  useEffect(() => {
    if (authenticated) {
      apiClient('/iam/me')
        .then((res: any) => res.json())
        .then(data => setBackendUser(data))
        .catch(err => console.error("Failed to fetch backend user", err));
    }
  }, [authenticated]);

  if (!authenticated) {
    return (
      <div className="status-card">
        <h2>Authentication</h2>
        <p>You are not logged in.</p>
        <button onClick={doLogin} className="login-button">Login</button>
      </div>
    );
  }

  return (
    <div className="status-card">
      <h2>User Profile</h2>
      <div className="status-info">
        <p><strong>Username:</strong> {getUsername()}</p>
        {backendUser && (
          <>
            <p><strong>Display Name:</strong> {backendUser.displayName}</p>
            <p><strong>Roles:</strong> {backendUser.roles?.join(', ')}</p>
          </>
        )}
      </div>
      <button onClick={doLogout} className="logout-button">Logout</button>
    </div>
  );
};

export default UserStatus;
