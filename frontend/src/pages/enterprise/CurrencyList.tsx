import React, { useEffect, useState } from 'react';
import { enterpriseApi, type Currency } from '../../api/enterprise';

const CurrencyList: React.FC = () => {
  const [currencies, setCurrencies] = useState<Currency[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    enterpriseApi.getCurrencies()
      .then(data => {
        setCurrencies(data);
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
      <h2>Currencies</h2>
      <table>
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Numeric Code</th>
            <th>Minor Units</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {currencies.map(curr => (
            <tr key={curr.code}>
              <td>{curr.code}</td>
              <td>{curr.name}</td>
              <td>{curr.numericCode}</td>
              <td>{curr.minorUnits}</td>
              <td>{curr.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CurrencyList;
