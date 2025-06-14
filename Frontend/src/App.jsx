import React, { useEffect, useState } from 'react';
import axios from 'axios';

const App = () => {
  const [algorithms, setAlgorithms] = useState([]);
  const [selectedAlgo, setSelectedAlgo] = useState(null);
  const [description, setDescription] = useState('');
  const [inputNumbers, setInputNumbers] = useState('');
  const [sortedNumbers, setSortedNumbers] = useState([]);

  // Algorithmen aus der Datenbank laden
  useEffect(() => {
    axios.get('http://localhost:8080/algorithms')
      .then(res => setAlgorithms(res.data))
      .catch(err => console.error('Fehler beim Laden der Algorithmen:', err));
  }, []);

  // Algorithmus-Auswahl aktualisieren
  const handleAlgorithmChange = (e) => {
    const algo = algorithms.find(a => a.id === parseInt(e.target.value));
    setSelectedAlgo(algo);
    setDescription(algo?.description || '');
  };

  // Sortierfunktion (POST an ShakerSort-Endpunkt)
  const handleSort = () => {
    const numberArray = inputNumbers
      .split(',')
      .map(n => parseInt(n.trim()))
      .filter(n => !isNaN(n));

    axios.post('http://localhost:8080/sort/shaker', numberArray)
      .then(res => setSortedNumbers(res.data))
      .catch(err => console.error('Fehler beim Sortieren:', err));
  };

  return (
    <div style={{ padding: '2rem', fontFamily: 'Arial' }}>
      <h1>🔢 Sortier-Algorithmen</h1>

      <div style={{ margin: '1rem 0' }}>
        <label htmlFor="algo-select">Algorithmus auswählen:</label><br/>
        <select id="algo-select" onChange={handleAlgorithmChange}>
          <option value="">-- bitte wählen --</option>
          {algorithms.map(algo => (
            <option key={algo.id} value={algo.id}>{algo.name}</option>
          ))}
        </select>
      </div>

      {description && (
        <div style={{ margin: '1rem 0', background: '#f0f0f0', padding: '1rem' }}>
          <strong>Beschreibung:</strong>
          <p>{description}</p>
        </div>
      )}

      <div style={{ margin: '1rem 0' }}>
        <label>Zahlen eingeben (z.B. 5, 3, 9):</label><br/>
        <input
          type="text"
          value={inputNumbers}
          onChange={(e) => setInputNumbers(e.target.value)}
          style={{ width: '100%' }}
        />
      </div>

      <button onClick={handleSort}>Sortieren</button>

      {sortedNumbers.length > 0 && (
        <div style={{ marginTop: '1rem' }}>
          <strong>Sortiertes Ergebnis:</strong> {sortedNumbers.join(', ')}
        </div>
      )}
    </div>
  );
};

export default App;
