<script>
  import { passwordStrength } from 'check-password-strength'

  let password
  let username

  function submit() {
    if(username == undefined || isEmptyOrSpaces(username)){
      alert("Username cannot be empty")
      return
    }

    if (
      passwordStrength(password).value == 'weak' ||
      passwordStrength(password).value == 'Too weak'
    ) {
      alert(
        'Your password is too weak make sure your password has atleast medium strength',
      )
      return
    }
    const user = {
      username: username,
      password: password,
    }

    fetch('http://localhost:727/api/v1/users/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    })
      .then((res) => res)
      .then((data) => {
        if (data.status == 201) {
          alert('Account created successfully')
          sessionStorage.setItem('token', username + ':' + password)
          window.location.href = '/home'
        } else {
          alert('Username already exists')
        }
      })
  }

  function isEmptyOrSpaces(str) {
		return str === null || str.match(/^ *$/) !== null;
	}
</script>

<div class="text-center">
  <div class="text-white font-bold pl-10 pr-10 pt-10">
    <h1 class="text-3xl ">Sign up</h1>
  </div>
  <form
    class="text-center pt-14 pr-14 pl-14 pb-7 sm:pl-96 sm:pr-96"
    onsubmit="return false;"
    id="form">
    <div class="mb-6">
      <label
        class="text-left block mb-2 text-sm font-medium text-white"
        for="username">
        Username
      </label>
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm
        rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
        dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text"
        placeholder=""
        id="username"
        bind:value={username}
        required />
    </div>
    <div class="mb-6">
      <label
        class="text-left block mb-2 text-sm font-medium text-white"
        for="password1">
        Password
      </label>
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm
        rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
        dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400
        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="password"
        placeholder=""
        id="password1"
        bind:value={password}
        required />
      <div class="text-left text-sm text-gray-500 dark:text-gray-400 mt-1">
        Password strength:
        <span class="text-green-500">{passwordStrength(password).value}</span>
        (Your password must have atleast medium strength)
      </div>
    </div>
    <input
      class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4
      focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm
      w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600
      dark:hover:bg-blue-700 dark:focus:ring-blue-800"
      type="submit"
      value="Register now" on:click={submit} />
  </form>
  <a href="/" class="text-white">Already have an account?</a>
</div>
