import React, { useEffect, useState } from 'react';
import { partyApi, type Party } from '../../api/party';

const PartyList: React.FC = () => {
  const [parties, setParties] = useState<Party[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    partyApi.getParties()
      .then(data => {
        setParties(data);
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
      <h2>Parties</h2>
      <table>
        <thead>
          <tr>
            <th>Party Number</th>
            <th>Type</th>
            <th>Display Name</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {parties.map(party => (
            <tr key={party.id}>
              <td>{party.partyNumber}</td>
              <td>{party.partyType}</td>
              <td>{party.displayName}</td>
              <td>{party.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PartyList;
