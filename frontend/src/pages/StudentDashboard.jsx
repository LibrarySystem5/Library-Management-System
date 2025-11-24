import { useState, useEffect } from 'react';
import axios from '../api/axios';
import { useAuth } from '../context/AuthContext';

const StudentDashboard = () => {
  const { user, logout } = useAuth();
  const [books, setBooks] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [fines, setFines] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('all');

  useEffect(() => {
    fetchBooks();
    fetchTransactions();
    fetchFines();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('/books');
      setBooks(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching books:', error);
      setLoading(false);
    }
  };

  const fetchTransactions = async () => {
    try {
      const response = await axios.get(`/transactions/student/${user.userId}`);
      setTransactions(response.data);
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  const fetchFines = async () => {
    try {
      const response = await axios.get(`/transactions/fines/student/${user.userId}`);
      setFines(response.data.totalFines);
    } catch (error) {
      console.error('Error fetching fines:', error);
    }
  };

  const handleBorrow = async (bookId) => {
    try {
      await axios.post('/transactions/borrow', {
        studentId: user.userId,
        bookId: bookId
      });
      fetchBooks();
      fetchTransactions();
      alert('Book borrowed successfully!');
    } catch (error) {
      alert(error.response?.data?.message || 'Failed to borrow book');
    }
  };

  const handleReturn = async (transactionId) => {
    try {
      await axios.post(`/transactions/return/${transactionId}`);
      fetchBooks();
      fetchTransactions();
      fetchFines();
      alert('Book returned successfully!');
    } catch (error) {
      alert(error.response?.data?.message || 'Failed to return book');
    }
  };

  const handleSearch = async () => {
    if (searchTerm.trim()) {
      try {
        const response = await axios.get(`/books/search?keyword=${searchTerm}`);
        setBooks(response.data);
      } catch (error) {
        console.error('Error searching books:', error);
      }
    } else {
      fetchBooks();
    }
  };

  const filteredBooks = activeTab === 'available' 
    ? books.filter(book => book.available)
    : books;

  const activeBorrowedBooks = transactions.filter(t => !t.returned);

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-teal-600 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold">Student Dashboard</h1>
            <p className="text-teal-100">Welcome, {user.name}</p>
          </div>
          <button
            onClick={logout}
            className="bg-teal-700 hover:bg-teal-800 px-4 py-2 rounded-lg font-semibold transition-colors"
          >
            Logout
          </button>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 py-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-teal-600">
            <h3 className="text-gray-600 text-sm font-medium">Total Books</h3>
            <p className="text-3xl font-bold text-teal-700">{books.length}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-yellow-500">
            <h3 className="text-gray-600 text-sm font-medium">Borrowed Books</h3>
            <p className="text-3xl font-bold text-yellow-600">{activeBorrowedBooks.length}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-red-500">
            <h3 className="text-gray-600 text-sm font-medium">Total Fines</h3>
            <p className="text-3xl font-bold text-red-600">KSh {fines.toFixed(2)}</p>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">Search Books</h2>
          <div className="flex gap-2">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              placeholder="Search by title or author..."
              className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
            />
            <button
              onClick={handleSearch}
              className="bg-teal-600 hover:bg-teal-700 text-white px-6 py-2 rounded-lg font-semibold transition-colors"
            >
              Search
            </button>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-bold text-gray-800">Library Books</h2>
            <div className="flex gap-2">
              <button
                onClick={() => setActiveTab('all')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'all' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                All Books
              </button>
              <button
                onClick={() => setActiveTab('available')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'available' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                Available Only
              </button>
            </div>
          </div>

          {loading ? (
            <p className="text-center text-gray-500 py-8">Loading books...</p>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {filteredBooks.map((book) => (
                <div key={book.id} className="border border-gray-200 rounded-lg p-4 hover:shadow-lg transition-shadow">
                  <h3 className="font-bold text-lg text-gray-800 mb-1">{book.title}</h3>
                  <p className="text-gray-600 text-sm mb-1">by {book.author}</p>
                  <p className="text-gray-500 text-xs mb-3">ISBN: {book.isbn}</p>
                  <div className="flex justify-between items-center">
                    <span className={`px-3 py-1 rounded-full text-xs font-semibold ${
                      book.available ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                    }`}>
                      {book.available ? 'Available' : 'Borrowed'}
                    </span>
                    {book.available && (
                      <button
                        onClick={() => handleBorrow(book.id)}
                        className="bg-teal-600 hover:bg-teal-700 text-white px-4 py-1 rounded text-sm font-semibold transition-colors"
                      >
                        Borrow
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">My Borrowed Books</h2>
          {activeBorrowedBooks.length === 0 ? (
            <p className="text-center text-gray-500 py-8">No borrowed books</p>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Book</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Borrow Date</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Due Date</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Fine</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Action</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {activeBorrowedBooks.map((transaction) => {
                    const borrowDate = new Date(transaction.borrowDate);
                    const dueDate = new Date(borrowDate);
                    dueDate.setDate(dueDate.getDate() + 30);
                    const isOverdue = new Date() > dueDate;
                    
                    return (
                      <tr key={transaction.id} className="hover:bg-gray-50">
                        <td className="px-4 py-3">{transaction.book.title}</td>
                        <td className="px-4 py-3">{borrowDate.toLocaleDateString()}</td>
                        <td className="px-4 py-3">
                          <span className={isOverdue ? 'text-red-600 font-semibold' : ''}>
                            {dueDate.toLocaleDateString()}
                          </span>
                        </td>
                        <td className="px-4 py-3">
                          <span className={transaction.fine > 0 ? 'text-red-600 font-semibold' : 'text-green-600'}>
                            KSh {transaction.fine.toFixed(2)}
                          </span>
                        </td>
                        <td className="px-4 py-3">
                          <button
                            onClick={() => handleReturn(transaction.id)}
                            className="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-1 rounded text-sm font-semibold transition-colors"
                          >
                            Return
                          </button>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;
