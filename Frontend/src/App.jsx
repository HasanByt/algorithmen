import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {
  Typography,
  TextField,
  Button,
  MenuItem,
  Box,
  Paper,
  Container,
  CircularProgress
} from '@mui/material';
import logo from '/algooo.png';

const backendUrl =
  window.location.hostname === 'localhost'
    ? 'http://localhost:8080'
    : 'https://authentic-stillness-production.up.railway.app';

const App = () => {
  const [algorithms, setAlgorithms] = useState([]);
  const [selectedAlgo, setSelectedAlgo] = useState(null);
  const [description, setDescription] = useState('');
  const [inputNumbers, setInputNumbers] = useState('');
  const [sortedNumbers, setSortedNumbers] = useState([]);
  const [sortDuration, setSortDuration] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    axios
      .get(`${backendUrl}/algorithms`)
      .then((res) => setAlgorithms(res.data))
      .catch((err) =>
        console.error('Fehler beim Laden der Algorithmen:', err)
      );
  }, []);

  const handleAlgorithmChange = (e) => {
    const algo = algorithms.find((a) => a.id === parseInt(e.target.value));
    setSelectedAlgo(algo);
    setDescription(algo?.description || '');
    setSortedNumbers([]);
    setSortDuration(null);
  };

  const handleSort = async () => {
    const numberArray = inputNumbers
      .split(',')
      .map((n) => parseInt(n.trim()))
      .filter((n) => !isNaN(n));

    if (!selectedAlgo) {
      alert('Bitte wähle einen Algorithmus aus.');
      return;
    }

    setLoading(true);

    try {
      const res = await axios.post(
        `${backendUrl}/sort/${selectedAlgo.name.toLowerCase()}`,
        numberArray
      );
      setSortedNumbers(res.data.sorted);
      setSortDuration(res.data.durationMs.toFixed(2));
    } catch (err) {
      console.error('Fehler beim Sortieren:', err);
    } finally {
      setLoading(false);
    }
  };


  return (
    <>
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4, mb: 2 }}>
        <Box
          component="img"
          src={logo}
          alt="algooo Logo"
          sx={{
            height: { xs: 120, sm: 160, md: 180 },
            maxWidth: { xs: 300, sm: 400, md: 500 },
            objectFit: 'contain'
          }}
        />
      </Box>

      <Container maxWidth="sm">
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
              <MenuItem value="" disabled>
                -- bitte auswählen --
              </MenuItem>
              {algorithms.map((algo) => (
                <MenuItem key={algo.id} value={algo.id}>
                  {algo.name}
                </MenuItem>
              ))}
            </TextField>
          </Box>

          {description && (
            <Box sx={{ mt: 2, p: 2, bgcolor: '#e3f2fd', borderRadius: 2 }}>
              <Typography variant="h6" fontWeight="bold">
                Beschreibung:
              </Typography>
              <Typography variant="body1">
                {description}
              </Typography>

              <Typography variant="h6" fontWeight="bold" sx={{ mt: 2 }}>
                Komplexität:
              </Typography>
              <Typography variant="body1">
                {selectedAlgo?.complexity || 'Nicht verfügbar'}
              </Typography>
            </Box>
          )}

          {selectedAlgo && (
            <Box sx={{ mt: 2, textAlign: 'center' }}>
              <img
                src={`/gifs/${selectedAlgo.name.toLowerCase()}.gif`}
                alt={`${selectedAlgo.name} GIF`}
                className="algorithm-gif"
              />
            </Box>
          )}

          <Box sx={{ mt: 3 }}>
            <TextField
              fullWidth
              label="Zahlen eingeben (z. B. 5, 3, 9)"
              value={inputNumbers}
              onChange={(e) => setInputNumbers(e.target.value)}
            />
          </Box>

          <Button
            fullWidth
            variant="contained"
            sx={{ mt: 3 }}
            onClick={handleSort}
            disabled={loading}
          >
            {loading ? <CircularProgress size={24} color="inherit" /> : 'Sortieren'}
          </Button>

          {sortedNumbers.length > 0 && (
            <Box sx={{ mt: 3, p: 2, bgcolor: '#e8f5e9', borderRadius: 2 }}>
              <Typography variant="subtitle1" fontWeight="bold">
                Sortiertes Ergebnis:
              </Typography>
              <Typography variant="body1">
                {sortedNumbers.join(', ')}
              </Typography>
              {sortDuration && (
                <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                  Sortierdauer: {sortDuration} ms
                </Typography>
              )}
            </Box>
          )}
        </Paper>
      </Container>
    </>
  );
};

export default App;
