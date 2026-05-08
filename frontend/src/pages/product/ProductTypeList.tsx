import React, { useEffect, useState } from 'react';
import { productApi, type ProductType } from '../../api/product';

const ProductTypeList: React.FC = () => {
  const [types, setTypes] = useState<ProductType[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    productApi.getProductTypes()
      .then(data => {
        setTypes(data);
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
      <h2>Product Types</h2>
      <table>
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Family</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {types.map(type => (
            <tr key={type.id}>
              <td>{type.code}</td>
              <td>{type.name}</td>
              <td>{type.productFamily}</td>
              <td>{type.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductTypeList;
