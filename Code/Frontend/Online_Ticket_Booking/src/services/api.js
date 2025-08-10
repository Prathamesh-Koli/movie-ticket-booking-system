import axios from 'axios'

const BASE_URL = 'http://localhost:8080/user' // Adjust as per backend

// Fetch shows for a specific theater owner
export const fetchOwnerShows = async (ownerId) => {
  try {
    const response = await axios.get(`${BASE_URL}/shows/manage/${ownerId}`)
    return response.data
  } catch (error) {
    console.error("Error fetching shows:", error)
    throw new Error("Failed to fetch shows")
  }
}

// Delete a show by ID
export const deleteShow = async (showId) => {
  try {
    const response = await axios.delete(`${BASE_URL}/${showId}`)
    return { success: true, message: response.data }
  } catch (error) {
    console.error("Error deleting show:", error)
    throw new Error("Failed to delete show")
  }
}

// Toggle show status (ACTIVE / SCHEDULED)
export const toggleShowStatus = async (showId, newStatus) => {
  try {
    const url = `${BASE_URL}/${showId}/${newStatus === "ACTIVE" ? "activate" : "deactivate"}`
    const response = await axios.put(url)
    return { success: true, message: response.data }
  } catch (error) {
    console.error(`Error toggling show status:`, error)
    throw new Error(`Failed to change show status`)
  }
}

// Fetch dashboard statistics for a specific theater owner
export const fetchDashboardStats = async () => {
  try {
    const token = localStorage.getItem("token")
    const response = await axios.get(`${BASE_URL}/dashboard/`,{
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
    return response.data
  } catch (error) {
    console.error("Error fetching dashboard stats:", error)
    throw new Error("Failed to load dashboard statistics")
  }
}