import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="p-4">
      <Input
        placeholder="Search..."
        value={filter}
        onChange={(e) => setFilter(e.target.value)}
      />
      <h2 className="text-xl font-bold mt-4">Products</h2>
      <div className="grid grid-cols-3 gap-4">
        {products
          .filter((p) => p.name.includes(filter))
          .map((product) => (
            <Card key={product.product_id}>
              <CardContent>
                <h3>{product.name}</h3>
                <p>Price: ${product.price}</p>
              </CardContent>
            </Card>
          ))}
      </div>
      <h2 className="text-xl font-bold mt-4">Categories</h2>
      <ul>
        {categories.map((category) => (
          <li key={category.category_id}>{category.name}</li>
        ))}
      </ul>
      <h2 className="text-xl font-bold mt-4">Transactions</h2>
      <ul>
        {transactions.map((transaction) => (
          <li key={transaction.transaction_id}>
            Transaction {transaction.transaction_id}: ${transaction.total_price}
          </li>
        ))}
      </ul>
    </div>
  );
}

//export default App;