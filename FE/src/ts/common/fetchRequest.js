const fetchRequest = (url, method, data) => {
  return fetch(url, {
      method: method,
      mode: 'cors',
      cache: 'no-cache',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
  });
}

export default fetchRequest;