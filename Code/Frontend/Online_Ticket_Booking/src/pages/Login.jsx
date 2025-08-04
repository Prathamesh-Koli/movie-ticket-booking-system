import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { loginUser } from '../services/user'
import { Container, Card, Form, Button } from 'react-bootstrap'

function Login() {
  const [info, setInfo] = useState({ email: '', password: '' })
  const navigate = useNavigate()

  const onLogin = async () => {
    const { email, password } = info

    if (!email) return toast.warn('Please enter Email!')
    if (!password) return toast.warn('Please enter Password!')

    const result = await loginUser(email, password)

    if (result?.status === 200) {
      const { token, ...userData } = result.data

      if (!token) {
        toast.error('Login successful but no token received from server!')
        return
      }

      // Store token & user in sessionStorage
      sessionStorage.setItem('token', token)
      sessionStorage.setItem('user', JSON.stringify(userData))

      console.log('Token saved:', token)

      toast.success(`Welcome to BooKar ${userData.firstname}!`)

      // Navigate after token is stored
      navigate('/profile')
    } else {
      toast.error(`Error: ${result?.data?.msg || 'Login failed'}`)
    }
  }

  return (
    <Container className='d-flex align-items-center justify-content-center' style={{ minHeight: '100vh' }}>
      <Card className='shadow-lg p-4 rounded' style={{ width: '100%', maxWidth: '400px' }}>
        <h2 className='text-center mb-4'>Login</h2>

        <Form>
          <Form.Group className='mb-3' controlId='formEmail'>
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type='email'
              placeholder='Enter email'
              value={info.email}
              onChange={(e) => setInfo({ ...info, email: e.target.value })}
            />
          </Form.Group>

          <Form.Group className='mb-3' controlId='formPassword'>
            <Form.Label>Password</Form.Label>
            <Form.Control
              type='password'
              placeholder='Password'
              value={info.password}
              onChange={(e) => setInfo({ ...info, password: e.target.value })}
            />
          </Form.Group>

          <div className='mb-3 text-end'>
            <span className='text-muted'>Don't have an account? </span>
            <Link to='/signup'>Register</Link>
          </div>

          <Button variant='primary' onClick={onLogin} className='w-100'>
            Login
          </Button>
        </Form>
      </Card>
    </Container>
  )
}

export default Login
