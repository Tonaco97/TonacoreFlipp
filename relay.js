// Adicione ao seu relay.js para interceptar os dados exfiltrados
app.post('/', (req, res) => {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
        const decoded = Buffer.from(body, 'base64').toString('utf8');
        console.log(`[EXFILTRATION DETECTED]: ${decoded}`); // Log ISO 8601 recomendado
        res.status(200).send('OK');
    });
});
