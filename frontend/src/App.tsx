import { useState, useEffect } from 'react'
import './App.css'
import UserStatus from './components/UserStatus'
import { apiClient } from './api/client'

function App() {
  const [health, setHealth] = useState<{ status: string, service: string, version: string } | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    apiClient('/health')
      .then((res: any) => {
        if (!res.ok) throw new Error('Failed to fetch health status');
        return res.json();
      })
      .then(data => setHealth(data))
      .catch(err => setError(err.message));
  }, []);

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>UFOS Platform</h1>
        <p className="subtitle">Unified Financial Operating System</p>
      </header>

      <main className="app-main">
        <div className="status-card">
          <h2>Foundation Setup Complete</h2>
          <div className="status-info">
            <p><strong>System Status:</strong> {health ? <span className="status-up">{health.status}</span> : error ? <span className="status-down">ERROR</span> : 'Checking...'}</p>
            {health && (
              <>
                <p><strong>Service:</strong> {health.service}</p>
                <p><strong>Version:</strong> {health.version}</p>
              </>
            )}
            {error && <p className="error-message">{error}</p>}
          </div>
        </div>

        <UserStatus />

        <div className="dashboard-placeholder">
          <h3>Dashboard Placeholder</h3>
          <p>This is where the financial dashboard will be implemented.</p>
          <div className="module-grid">
            <div className="module-box">Core Banking</div>
            <div className="module-box">Lending</div>
            <div className="module-box">Treasury</div>
            <div className="module-box">Payments</div>
          </div>
        </div>
      </main>
    </div>
  )
}

export default App
