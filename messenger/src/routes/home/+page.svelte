<script>
  import { onMount } from 'svelte'
  let messages = []
  let sentMessages = []
  let usernames = []

  onMount(async () => {
    const response = await fetch(
      'http://localhost:1567/api/v1/messages/received',
      {
        method: 'GET',
        headers: {
          Credentials: sessionStorage.getItem('token'),
        },
      },
    )
    messages = await response.json()
    console.log(messages)

    const responseSent = await fetch(
      'http://localhost:1567/api/v1/messages/sent',
      {
        method: 'GET',
        headers: {
          Credentials: sessionStorage.getItem('token'),
        },
      },
    )
    sentMessages = await responseSent.json()
    console.log(sentMessages)

    const responseUsernames = await fetch('http://localhost:1567/api/v1/users', {
      method: 'GET',
      headers: {
        Credentials: sessionStorage.getItem('token'),
      },
    })
    usernames = await responseUsernames.json()
    console.log(usernames)
  })

  function deleteSent(id) {
    fetch('http://localhost:1567/api/v1/messages/sent/' + id, {
      method: 'DELETE',
      headers: {
        Credentials: sessionStorage.getItem('token'),
      },
    })
      .then((res) => res)
      .then((data) => {
        if (data.status === 204) {
          console.log('deletion success')
          window.location.href = '/home'
        } else {
          alert('Invalid credentials')
        }
      })
  }

  function deleteReceived(id) {
    fetch('http://localhost:1567/api/v1/messages/received/' + id, {
      method: 'DELETE',
      headers: {
        Credentials: sessionStorage.getItem('token'),
      },
    })
      .then((res) => res)
      .then((data) => {
        if (data.status === 204) {
          console.log('deletion success')
          window.location.href = '/home'
        } else {
          alert('Invalid credentials')
        }
      })
  }

  let subject
  let message
  let recipients

  function sendMessage() {
    fetch('http://localhost:1567/api/v1/messages/send', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Credentials: sessionStorage.getItem('token'),
      },
      body: JSON.stringify({ subject, message, recipients }),
    })
      .then((res) => res)
      .then((data) => {
        if (data.status === 200) {
          console.log('message sent')
          window.location.href = '/home'
        } else {
          alert('Invalid credentials')
        }
      })
  }
</script>

<div class="border border-white p-10 m-10">
  <h1>Sent messages</h1>
  <div class="overflow-auto h-96">
    {#each sentMessages as message}
      <div class="border border-white p-10 m-10">
        <h2 class="font-bold">Sender: {message.sender}</h2>
        <h2 class="font-bold">Recipients: {message.recipients}</h2>
        <h2 class="font-bold">Subject: {message.subject}</h2>
        <h2 class="font-bold">Sent at: {message.timestamp}</h2>
        <p>{message.message}</p>
        <button
          on:click={() => {
            deleteSent(message.id)
          }}
          class="border border-red-600 bg-red-600 p-1 mt-2 rounded-full">
          Delete message
        </button>
      </div>
    {/each}
  </div>
</div>
<div class="border border-white p-10 m-10">
  <h1>Received messages</h1>
  <div class="overflow-auto h-96">
    {#each messages as message}
      <div class="border border-white p-10 m-10">
        <h2 class="font-bold">Sender: {message.sender}</h2>
        <h2 class="font-bold">Recipients: {message.recipients}</h2>
        <h2 class="font-bold">Subject: {message.subject}</h2>
        <h2 class="font-bold">Sent at: {message.timestamp}</h2>
        <p>{message.message}</p>
        <button
          on:click={() => {
            deleteReceived(message.id)
          }}
          class="border border-red-600 bg-red-600 p-1 mt-2 rounded-full">
          Delete message
        </button>
      </div>
    {/each}
  </div>
</div>

<form class="flex flex-col border border-white p-10 m-10">
  <h1 class="text-3xl font-bold underline">Send message</h1>
  <label class="mt-5" for="subject">Subject</label>
  <input
    class="text-black mb-5"
    type="text"
    bind:value={subject}
    placeholder="Enter subject"
    required />
  <label for="message">Message</label>
  <input
    class="text-black mb-5"
    type="text"
    bind:value={message}
    placeholder="Enter message"
    required />
  <label for="recipients">Recipients(hold CTRL to select multiple)</label>
  <select bind:value={recipients} multiple required>
    {#each usernames as username}
      <option class="text-black" value={username}>{username}</option>
    {/each}
  </select>
  <button class="text-white mt-5 bg-blue-700 hover:bg-blue-800 focus:ring-4
  focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm
  w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600
  dark:hover:bg-blue-700 dark:focus:ring-blue-800" on:click={sendMessage}>Send</button>
</form>
