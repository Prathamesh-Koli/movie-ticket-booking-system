import axios from 'axios'
import { config } from './config'
import { toast } from 'react-toastify'

export async function loginUser(email, password) {
  try {
    const url = `${config.serverUrl}/user/signin`
    const body = { email, password }
    const response = await axios.post(url, body)
    return response
  } catch (error) {
    return error.response
  }
}

export async function registerUser(
  firstname,
  lastname,
  email,
  password,
  mobile_no,
  gender,
  role,
  dob,
  addr_line1,
  addr_line2,
  town_city,
  state,
  pincode,
  district
) {
  try {
    const url = `${config.serverUrl}/user/register`
    const body = {
      firstname,
      lastname,
      email,
      password,
      mobile_no,
      gender,
      role,
      dob,
      address: {
        addr_line1,
        addr_line2,
        town_city,
        state,
        pincode,
        district
      }
    }

    const response = await axios.post(url, body)
    return response
  } catch (error) {
    return error.response
  }
}

export async function fetchUserDetails() {
  const token = sessionStorage.getItem("token")

  if (!token) {
    console.warn("No token found in session storage")
    return null
  }

  try {
    const response = await axios.get(`${config.serverUrl}/user/profile`, {
      headers: { Authorization: `Bearer ${token}` },
    })

    const userData = response.data
    sessionStorage.setItem("user", JSON.stringify(userData))
    return userData
  } catch (error) {
    toast.error(`Error: ${error?.response?.data?.msg || 'Failed to fetch profile'}`)
    return null
  }
}
