import { useState, useEffect } from 'react';
import axios from '../api/axios';
import { useAuth } from '../context/AuthContext';

const LibrarianDashboard = () => {
  const { user, logout } = useAuth();
  const [books, setBooks] = useState([]);
  const [users, setUsers] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [totalFines, setTotalFines] = useState(0);
  const [activeTab, setActiveTab] = useState('books');
  const [showAddModal, setShowAddModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [bookForm, setBookForm] = useState({ title: '', author: '', isbn: '' });

  useEffect(() => {
    fetchBooks();
    fetchUsers();
    fetchTransactions();
    fetchTotalFines();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('/books');
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await axios.get('/users');
      setUsers(response.data.filter(u => u.userType === 'STUDENT'));
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const fetchTransactions = async () => {
    try {
      const response = await axios.get('/transactions/all');
      setTransactions(response.data);
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  const fetchTotalFines = async () => {
    try {
      const response = await axios.get('/transactions/fines/all');
      setTotalFines(response.data.totalFines);
    } catch (error) {
      console.error('Error fetching fines:', error);
    }
  };

  const handleAddBook = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/books', bookForm);
      setShowAddModal(false);
      setBookForm({ title: '', author: '', isbn: '' });
      fetchBooks();
      alert('Book added successfully!');
    } catch (error) {
      alert('Failed to add book');
    }
  };

  const handleUpdateBook = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`/books/${editingBook.id}`, bookForm);
      setEditingBook(null);
      setBookForm({ title: '', author: '', isbn: '' });
      fetchBooks();
      alert('Book updated successfully!');
    } catch (error) {
      alert('Failed to update book');
    }
  };

  const handleDeleteBook = async (id) => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      try {
        await axios.delete(`/books/${id}`);
        fetchBooks();
        alert('Book deleted successfully!');
      } catch (error) {
        alert('Failed to delete book');
      }
    }
  };

  const handleReturnBook = async (transactionId) => {
    try {
      await axios.post(`/transactions/return/${transactionId}`);
      fetchBooks();
      fetchTransactions();
      fetchTotalFines();
      alert('Book returned successfully!');
    } catch (error) {
      alert('Failed to return book');
    }
  };

  const startEdit = (book) => {
    setEditingBook(book);
    setBookForm({ title: book.title, author: book.author, isbn: book.isbn });
  };

  const activeBorrowedTransactions = transactions.filter(t => !t.returned);

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-teal-600 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold">Librarian Dashboard</h1>
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
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-teal-600">
            <h3 className="text-gray-600 text-sm font-medium">Total Books</h3>
            <p className="text-3xl font-bold text-teal-700">{books.length}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-blue-500">
            <h3 className="text-gray-600 text-sm font-medium">Total Students</h3>
            <p className="text-3xl font-bold text-blue-600">{users.length}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-yellow-500">
            <h3 className="text-gray-600 text-sm font-medium">Active Borrows</h3>
            <p className="text-3xl font-bold text-yellow-600">{activeBorrowedTransactions.length}</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md border-l-4 border-red-500">
            <h3 className="text-gray-600 text-sm font-medium">Total Fines</h3>
            <p className="text-3xl font-bold text-red-600">KSh {totalFines.toFixed(2)}</p>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex justify-between items-center mb-6 border-b pb-4">
            <div className="flex gap-2">
              <button
                onClick={() => setActiveTab('books')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'books' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                Manage Books
              </button>
              <button
                onClick={() => setActiveTab('users')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'users' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                View Users
              </button>
              <button
                onClick={() => setActiveTab('transactions')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'transactions' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                Active Borrows
              </button>
              <button
                onClick={() => setActiveTab('fines')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  activeTab === 'fines' ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-700'
                }`}
              >
                View Fines
              </button>
            </div>
            {activeTab === 'books' && (
              <button
                onClick={() => setShowAddModal(true)}
                className="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-lg font-semibold transition-colors"
              >
                + Add Book
              </button>
            )}
          </div>

          {activeTab === 'books' && (
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Title</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Author</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">ISBN</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Status</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Actions</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {books.map((book) => (
                    <tr key={book.id} className="hover:bg-gray-50">
                      <td className="px-4 py-3">{book.title}</td>
                      <td className="px-4 py-3">{book.author}</td>
                      <td className="px-4 py-3">{book.isbn}</td>
                      <td className="px-4 py-3">
                        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${
                          book.available ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                        }`}>
                          {book.available ? 'Available' : 'Borrowed'}
                        </span>
                      </td>
                      <td className="px-4 py-3">
                        <div className="flex gap-2">
                          <button
                            onClick={() => startEdit(book)}
                            className="bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded text-sm font-semibold transition-colors"
                          >
                            Edit
                          </button>
                          <button
                            onClick={() => handleDeleteBook(book.id)}
                            className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded text-sm font-semibold transition-colors"
                          >
                            Delete
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {activeTab === 'users' && (
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Name</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Email</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Borrowed Books</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {users.map((student) => {
                    const userBorrows = activeBorrowedTransactions.filter(t => t.student.id === student.id);
                    return (
                      <tr key={student.id} className="hover:bg-gray-50">
                        <td className="px-4 py-3">{student.name}</td>
                        <td className="px-4 py-3">{student.email}</td>
                        <td className="px-4 py-3">
                          {userBorrows.length > 0 ? (
                            <div className="text-sm">
                              {userBorrows.map(t => {
                                const borrowDate = new Date(t.borrowDate);
                                const dueDate = new Date(borrowDate);
                                dueDate.setDate(dueDate.getDate() + 30);
                                const isOverdue = new Date() > dueDate;
                                
                                return (
                                  <div key={t.id} className="mb-1">
                                    {t.book.title} (Due: <span className={isOverdue ? 'text-red-600 font-semibold' : ''}>{dueDate.toLocaleDateString()}</span>)
                                  </div>
                                );
                              })}
                            </div>
                          ) : (
                            <span className="text-gray-500">None</span>
                          )}
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          )}

          {activeTab === 'transactions' && (
            <div className="overflow-x-auto">
              <h3 className="text-lg font-bold text-gray-800 mb-4">Active Borrowed Books</h3>
              {activeBorrowedTransactions.length === 0 ? (
                <p className="text-center text-gray-500 py-8">No active borrows</p>
              ) : (
                <table className="w-full">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Student</th>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Book</th>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Borrow Date</th>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Due Date</th>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Current Fine</th>
                      <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Action</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    {activeBorrowedTransactions.map((transaction) => {
                      const borrowDate = new Date(transaction.borrowDate);
                      const dueDate = new Date(borrowDate);
                      dueDate.setDate(dueDate.getDate() + 30);
                      const currentFine = transaction.fine || 0;
                      const isOverdue = new Date() > dueDate;
                      
                      return (
                        <tr key={transaction.id} className="hover:bg-gray-50">
                          <td className="px-4 py-3">{transaction.student.name}</td>
                          <td className="px-4 py-3">{transaction.book.title}</td>
                          <td className="px-4 py-3">{borrowDate.toLocaleDateString()}</td>
                          <td className="px-4 py-3">
                            <span className={isOverdue ? 'text-red-600 font-semibold' : ''}>
                              {dueDate.toLocaleDateString()}
                            </span>
                          </td>
                          <td className="px-4 py-3">
                            <span className={currentFine > 0 ? 'text-red-600 font-semibold' : 'text-green-600'}>
                              KSh {currentFine.toFixed(2)}
                            </span>
                          </td>
                          <td className="px-4 py-3">
                            <button
                              onClick={() => handleReturnBook(transaction.id)}
                              className="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-1 rounded text-sm font-semibold transition-colors"
                            >
                              Return Book
                            </button>
                          </td>
                        </tr>
                      );
                    })}
                  </tbody>
                </table>
              )}
            </div>
          )}

          {activeTab === 'fines' && (
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Student</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Book</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Borrow Date</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Status</th>
                    <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Fine</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {transactions.filter(t => t.fine > 0).map((transaction) => (
                    <tr key={transaction.id} className="hover:bg-gray-50">
                      <td className="px-4 py-3">{transaction.student.name}</td>
                      <td className="px-4 py-3">{transaction.book.title}</td>
                      <td className="px-4 py-3">{new Date(transaction.borrowDate).toLocaleDateString()}</td>
                      <td className="px-4 py-3">
                        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${
                          transaction.returned ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'
                        }`}>
                          {transaction.returned ? 'Returned' : 'Active'}
                        </span>
                      </td>
                      <td className="px-4 py-3">
                        <span className="text-red-600 font-semibold">KSh {transaction.fine.toFixed(2)}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>

      {(showAddModal || editingBook) && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <h2 className="text-xl font-bold mb-4">{editingBook ? 'Edit Book' : 'Add New Book'}</h2>
            <form onSubmit={editingBook ? handleUpdateBook : handleAddBook} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
                <input
                  type="text"
                  value={bookForm.title}
                  onChange={(e) => setBookForm({ ...bookForm, title: e.target.value })}
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Author</label>
                <input
                  type="text"
                  value={bookForm.author}
                  onChange={(e) => setBookForm({ ...bookForm, author: e.target.value })}
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">ISBN</label>
                <input
                  type="text"
                  value={bookForm.isbn}
                  onChange={(e) => setBookForm({ ...bookForm, isbn: e.target.value })}
                  required
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                />
              </div>
              <div className="flex gap-2 justify-end">
                <button
                  type="button"
                  onClick={() => {
                    setShowAddModal(false);
                    setEditingBook(null);
                    setBookForm({ title: '', author: '', isbn: '' });
                  }}
                  className="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded-lg font-semibold transition-colors"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-teal-600 hover:bg-teal-700 text-white rounded-lg font-semibold transition-colors"
                >
                  {editingBook ? 'Update' : 'Add'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default LibrarianDashboard;
