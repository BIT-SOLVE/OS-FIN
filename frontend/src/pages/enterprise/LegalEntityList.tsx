import React, { useEffect, useState } from 'react';
import { enterpriseApi, type LegalEntity } from '../../api/enterprise';

const LegalEntityList: React.FC = () => {
  const [entities, setEntities] = useState<LegalEntity[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    enterpriseApi.getLegalEntities()
      .then(data => {
        setEntities(data);
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
      <h2>Legal Entities</h2>
      <table>
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Country</th>
            <th>Currency</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {entities.map(entity => (
            <tr key={entity.id}>
              <td>{entity.code}</td>
              <td>{entity.name}</td>
              <td>{entity.countryCode}</td>
              <td>{entity.baseCurrencyCode}</td>
              <td>{entity.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default LegalEntityList;
