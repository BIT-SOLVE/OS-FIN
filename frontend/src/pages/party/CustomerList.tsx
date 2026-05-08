import React, { useEffect, useState } from 'react';
import { partyApi, type Customer } from '../../api/party';

const CustomerList: React.FC = () => {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    partyApi.getCustomers()
      .then(data => {
        setCustomers(data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h2>Customers</h2>
      <table>
        <thead>
          <tr>
            <th>Customer Number</th>
            <th>Type</th>
            <th>Onboarding Status</th>
            <th>Status</th>
            <th>Risk Rating</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(customer => (
            <tr key={customer.id}>
              <td>{customer.customerNumber}</td>
              <td>{customer.customerType}</td>
              <td>{customer.onboardingStatus}</td>
              <td>{customer.customerStatus}</td>
              <td>{customer.riskRating}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CustomerList;
