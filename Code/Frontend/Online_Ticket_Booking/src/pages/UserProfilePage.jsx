import React, { useEffect, useState } from 'react'
import { fetchUserDetails } from '../services/user'
import { Container, Card, Spinner } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

function UserProfilePage() {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    const token = sessionStorage.getItem('token')

    if (!token) {
      console.warn('No token found — redirecting to login')
      navigate('/login')
      return
    }

    const getUserDetails = async () => {
      setLoading(true)
      const data = await fetchUserDetails()
      if (data) {
        setUser(data)
      }
      setLoading(false)
    }

    getUserDetails()
  }, [navigate])

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <Spinner animation="border" />
      </Container>
    )
  }

  if (!user) {
    return (
      <Container className="text-center mt-5">
        <h4>Could not load user profile</h4>
      </Container>
    )
  }

  return (
    <Container className="mt-5">
      <Card className="p-4 shadow-lg">
        <h3>{user.firstname} {user.lastname}</h3>
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Mobile:</strong> {user.mobile_no}</p>
        <p><strong>Gender:</strong> {user.gender}</p>
        <p><strong>Role:</strong> {user.role}</p>
      </Card>
    </Container>
  )
}

export default UserProfilePage
