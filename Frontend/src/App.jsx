import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, TextField, Button, MenuItem, Box, Paper } from '@mui/material';

const App = () => {
  const [algorithms, setAlgorithms] = useState([]);
  const [selectedAlgo, setSelectedAlgo] = useState(null);
  const [description, setDescription] = useState('');
  const [inputNumbers, setInputNumbers] = useState('');
  const [sortedNumbers, setSortedNumbers] = useState([]);
  const [sortDuration, setSortDuration] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/algorithms')
      .then(res => setAlgorithms(res.data))
      .catch(err => console.error('Fehler beim Laden der Algorithmen:', err));
  }, []);

  const handleAlgorithmChange = (e) => {
    const algo = algorithms.find(a => a.id === parseInt(e.target.value));
    setSelectedAlgo(algo);
    setDescription(algo?.description || '');
  };

  const handleSort = async () => {
    const numberArray = inputNumbers
      .split(',')
      .map(n => parseInt(n.trim()))
      .filter(n => !isNaN(n));

    if (!selectedAlgo) {
      alert('Bitte wähle einen Algorithmus aus.');
      return;
    }

    const start = performance.now();

    try {
      const res = await axios.post(`http://localhost:8080/sort/${selectedAlgo.name.toLowerCase()}`, numberArray);
      const end = performance.now();
      setSortedNumbers(res.data);
      setSortDuration((end - start).toFixed(2));
    } catch (err) {
      console.error('Fehler beim Sortieren:', err);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 4 }}>
        <Typography variant="h4" gutterBottom align="center">
          Sortier-Algorithmen
        </Typography>

        <Box sx={{ mt: 2 }}>
          <TextField
            select
            fullWidth
            label="Algorithmus auswählen"
            onChange={handleAlgorithmChange}
            value={selectedAlgo?.id || ''}
          >
            {algorithms.map(algo => (
              <MenuItem key={algo.id} value={algo.id}>{algo.name}</MenuItem>
            ))}
          </TextField>
        </Box>

        {description && (
          <Box sx={{ mt: 2, p: 2, bgcolor: '#e3f2fd', borderRadius: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold">Beschreibung:</Typography>
            <Typography variant="body2">{description}</Typography>
          </Box>
        )}

        {selectedAlgo && (
          <Box sx={{ mt: 2, textAlign: 'center' }}>
            <img
              src={`/gifs/${selectedAlgo.name.toLowerCase()}.gif`}
              alt={`${selectedAlgo.name} GIF`}
              style={{ maxWidth: '100%', borderRadius: '8px' }}
            />
          </Box>
        )}

        <Box sx={{ mt: 3 }}>
          <TextField
            fullWidth
            label="Zahlen eingeben (z.B. 5, 3, 9)"
            value={inputNumbers}
            onChange={(e) => setInputNumbers(e.target.value)}
          />
        </Box>

        <Button
          fullWidth
          variant="contained"
          sx={{ mt: 3 }}
          onClick={handleSort}
        >
          Sortieren
        </Button>

        {sortedNumbers.length > 0 && (
          <Box sx={{ mt: 3, p: 2, bgcolor: '#e8f5e9', borderRadius: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold">Sortiertes Ergebnis:</Typography>
            <Typography variant="body1">{sortedNumbers.join(', ')}</Typography>
            {sortDuration && (
              <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                Sortierdauer: {sortDuration} ms
              </Typography>
            )}
          </Box>
        )}
      </Paper>
    </Container>
  );
};

export default App;
