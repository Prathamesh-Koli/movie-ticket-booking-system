import { useEffect, useState } from "react"
import { Container, Row, Col, Card, Nav, Button, Form, Badge, Alert } from "react-bootstrap"
import { User, Calendar, CreditCard, Settings, Ticket, KeyRound } from "lucide-react"
import userImage from '../assets/9815472.png'
import { updateUser, updatePassword, fetchUserDetails } from "../services/user"
import { toast } from "react-toastify"
import axios from 'axios'
import { config } from '../services/config'
// import { useBooking } from "../contexts/BookingContext"
import { useNavigate } from "react-router-dom"

function UserProfilePage() {
    const navigate = useNavigate()
    // const { bookings } = useBooking()
    const [activeTab, setActiveTab] = useState("profile")
    const [user, setUser] = useState(null)
    const [newPass, setNewPass] = useState({ id: 0, oldPassword: "", newPassword: "" })
    const [profileData, setProfileData] = useState({
        id: 0,
        firstname: "",
        lastname: "",
        email: "",
        password: "",
        mobile_no: "",
        dob: "",
        gender: "",
        role: "ROLE_CUTOMER",
        address: {
            addr_line1: "",
            addr_line2: "",
            town_city: "",
            state: "",
            pincode: "",
            district: ""
        }

    })

    useEffect(() => {
        const fetchDetails = async () => {
            const userData = await fetchUserDetails()
            if (userData != null) {
                // Update state if needed
                setUser(userData);

                setNewPass({
                    id: userData.id,
                    newPassword: "",
                    oldPassword: ""
                })

                // Initialize profileData with server-fetched values
                setProfileData({
                    id: userData.id,
                    firstname: userData.firstname,
                    lastname: userData.lastname,
                    email: userData.email,
                    password: userData.password,
                    mobile_no: userData.mobile_no || "",
                    dob: userData.dob || "",
                    gender: userData.gender,
                    address: {
                        addr_line1: userData.address.addr_line1,
                        addr_line2: userData.address.addr_line2,
                        town_city: userData.address.town_city,
                        state: userData.address.state,
                        district: userData.address.district,
                        pincode: userData.address.pincode
                    }
                })
            } else {
                toast.error("Failed to Load the User")
            }

        };

        fetchDetails();
    }, []);


    const handleProfileUpdate = async (e) => {
        e.preventDefault()
        const { id, firstname, lastname, email, password, mobile_no, gender, role, dob, address: { addr_line1, addr_line2, town_city, state, pincode, district } } = profileData


        if (firstname.length == 0) {
            alert("Please Enter Firstname !!")
        } else if (lastname.length == 0) {
            alert("Please Enter Lasttname !!")
        } else if (email.length == 0) {
            alert("Please Enter Email !!")
        } else if (password.length == 0) {
            alert("Please Enter Password !!")
        } else if (mobile_no.length == 0) {
            alert("Please Enter Mobile No. !!")
        } else if (gender.length == 0) {
            alert("Please Enter Gender !!")
        } else if (addr_line1.length == 0) {
            alert("Please Enter Address !!")
        } else if (town_city.length == 0) {
            alert("Please Enter Your Town/City !!")
        } else if (district.length == 0) {
            alert("Please Enter The District !!")
        } else if (state.length == 0) {
            alert("Please Enter The State !!")
        }


        const result = await updateUser(id, firstname, lastname, email, password, mobile_no, gender, role, dob, addr_line1, addr_line2, town_city, state, pincode, district)
        if (result.status == 201) {
            toast.success(`${result.data.firstname} Details Updated..!`)
            sessionStorage.setItem('user', JSON.stringify(result.data))
            navigate("/profile")
        } else {
            toast.error(`Error: ${result.data.msg}`)
        }
    }

    const changePassword = async (e) => {
        e.preventDefault()
        const { id, newPassword, oldPassword } = newPass
        if (oldPassword == newPassword) {
            toast.warn("Warning: New Password Must Be Different from Current Password !!!")
        } else {
            const result = await updatePassword(id, oldPassword, newPassword)
            if (result.status == 201) {
                toast.success(`Success: ${result.data.msg}`)
                navigate("/profile")
            } else {
                toast.error(`Error: ${result.data.msg}`)
            }
        }
    }


    const logout = () => {
        sessionStorage.removeItem("user")
        localStorage.removeItem("token")
        navigate("/signin")
    }

    if (!user) {
        return (
            <Container className="py-5">
                <Alert variant="warning" className="text-center">
                    <h4>Please login to view your profile</h4>
                </Alert>
            </Container>
        )
    }

    return (
        <Container className="py-4">
            <Row>
                <Col md={3} className="mb-4">
                    <Card>
                        <Card.Body className="text-center">
                            <img
                                src={userImage}
                                alt={user.name}
                                className="rounded-circle mb-3"
                                style={{ width: "100px", height: "100px", objectFit: "cover" }}
                            />
                            <h5 className="mb-1">{user.name}</h5>
                            <p className="text-muted small mb-3">{user.email}</p>
                            <Badge bg="primary" className="mb-3">
                                {user.firstname}
                            </Badge>

                            <Nav variant="pills" className="flex-column">
                                <Nav.Item className="mb-2">
                                    <Nav.Link
                                        active={activeTab === "profile"}
                                        onClick={() => setActiveTab("profile")}
                                        className="d-flex align-items-center"
                                    >
                                        <User size={18} className="me-2" />
                                        Profile
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link
                                        active={activeTab === "Change Password"}
                                        onClick={() => setActiveTab("Change Password")}
                                        className="d-flex align-items-center"
                                    >
                                        <KeyRound size={18} className="me-2" />
                                        Change Password
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item className="mb-2">
                                    <Nav.Link
                                        active={activeTab === "bookings"}
                                        onClick={() => setActiveTab("bookings")}
                                        className="d-flex align-items-center"
                                    >
                                        <Ticket size={18} className="me-2" />
                                        My Bookings
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item className="mb-2">
                                    <Nav.Link
                                        active={activeTab === "payments"}
                                        onClick={() => setActiveTab("payments")}
                                        className="d-flex align-items-center"
                                    >
                                        <CreditCard size={18} className="me-2" />
                                        Payment Methods
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link
                                        active={activeTab === "settings"}
                                        onClick={() => setActiveTab("settings")}
                                        className="d-flex align-items-center"
                                    >
                                        <Settings size={18} className="me-2" />
                                        Settings
                                    </Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={9}>
                    {/*{activeTab === "bookings" && (
                        <Card>
                            <Card.Header>
                                <h5 className="mb-0">My Bookings</h5>
                            </Card.Header>
                            <Card.Body>
                                {bookings.length === 0 ? (
                                    <div className="text-center py-5">
                                        <Ticket size={48} className="text-muted mb-3" />
                                        <h5>No bookings yet</h5>
                                        <p className="text-muted">Start booking your favorite movies!</p>
                                        <Button variant="primary" href="/">
                                            Browse Movies
                                        </Button>
                                    </div>
                                ) : (
                                    <div className="space-y-3">
                                        {bookings.map((booking) => (
                                            <Card key={booking.id} className="mb-3">
                                                <Card.Body>
                                                    <Row>
                                                        <Col md={8}>
                                                            <h6 className="mb-2">Avengers: Endgame</h6>
                                                            <p className="text-muted small mb-1">PVR Cinemas - Phoenix Mall | Screen 1</p>
                                                            <p className="text-muted small mb-2">
                                                                <Calendar size={14} className="me-1" />
                                                                {new Date(booking.bookingDate).toLocaleDateString()} | 8:30 PM
                                                            </p>
                                                            <div className="d-flex gap-1 mb-2">
                                                                {booking.seats.map((seat) => (
                                                                    <Badge key={seat.id} bg="primary">
                                                                        {seat.row}
                                                                        {seat.number}
                                                                    </Badge>
                                                                ))}
                                                            </div>
                                                        </Col>
                                                        <Col md={4} className="text-end">
                                                            <h6 className="text-success">₹{booking.totalAmount}</h6>
                                                            <Badge bg={booking.status === "confirmed" ? "success" : "danger"} className="mb-2">
                                                                {booking.status}
                                                            </Badge>
                                                            <div>
                                                                <Button variant="outline-primary" size="sm">
                                                                    Download Ticket
                                                                </Button>
                                                            </div>
                                                        </Col>
                                                    </Row>
                                                </Card.Body>
                                            </Card>
                                        ))}
                                    </div>
                                )}
                            </Card.Body>
                        </Card>
                    )}*/}

                    {activeTab === "Change Password" && (
                        <Card>
                            <Card.Header>
                                <h5><b>Update Password</b></h5>
                            </Card.Header>
                            <Card.Body>
                                <Form onSubmit={changePassword}>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Password</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    placeholder="Enter The Old Password"
                                                    onChange={(e) => setNewPass({ ...newPass, oldPassword: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>New Password</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    placeholder="Enter The New Password"
                                                    onChange={(e) => setNewPass({ ...newPass, newPassword: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row><br /></Row>
                                    <Button type="submit" variant="primary">
                                        Change Password
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                    )
                    }

                    {activeTab === "profile" && (
                        <Card>
                            <Card.Header>
                                <h5 className="mb-0"><b>User Information</b></h5>
                            </Card.Header>
                            <Card.Body style={{ maxHeight: "43vh", overflowY: "auto", paddingRight: "1rem" }}>
                                <Form onSubmit={handleProfileUpdate}>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>First Name</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.firstname}
                                                    onChange={(e) => setProfileData({ ...profileData, firstname: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Last Name</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.lastname}
                                                    onChange={(e) => setProfileData({ ...profileData, lastname: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Email</Form.Label>
                                                <Form.Control
                                                    type="email"
                                                    value={profileData.email}
                                                    onChange={(e) => setProfileData({ ...profileData, email: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Phone Number</Form.Label>
                                                <Form.Control
                                                    type="tel"
                                                    value={profileData.mobile_no}
                                                    onChange={(e) => setProfileData({ ...profileData, mobile_no: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Date of Birth</Form.Label>
                                                <Form.Control
                                                    type="date"
                                                    value={profileData.dob}
                                                    onChange={(e) => setProfileData({ ...profileData, dob: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Gender</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.gender}
                                                    onChange={(e) => setProfileData({ ...profileData, gender: e.target.value })}
                                                />
                                            </Form.Group>
                                        </Col>

                                    </Row>
                                    <Row><br /></Row>
                                    <Row>
                                        <h5 className="mb-0"><b>Address Details</b></h5>
                                    </Row>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Address Line 1</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.addr_line1}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, addr_line1: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Address Line 2</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.addr_line2}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, addr_line2: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>City/Town</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.town_city}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, town_city: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>District</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.district}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, district: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>State</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.state}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, state: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col md={6} className="mb-3">
                                            <Form.Group>
                                                <Form.Label>Pincode</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    value={profileData.address.pincode}
                                                    onChange={(e) => setProfileData({ ...profileData, address: { ...profileData.address, pincode: e.target.value } })}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Button type="submit" variant="primary">
                                        Update Profile
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                    )}

                    {activeTab === "payments" && (
                        <Card>
                            <Card.Header>
                                <h5 className="mb-0">Payment Methods</h5>
                            </Card.Header>
                            <Card.Body>
                                <div className="text-center py-5">
                                    <CreditCard size={48} className="text-muted mb-3" />
                                    <h5>No saved payment methods</h5>
                                    <p className="text-muted">Add a payment method for faster checkout</p>
                                    <Button variant="primary">Add Payment Method</Button>
                                </div>
                            </Card.Body>
                        </Card>
                    )}

                    {activeTab === "settings" && (
                        <Card>
                            <Card.Header>
                                <h5 className="mb-0">Account Settings</h5>
                            </Card.Header>
                            <Card.Body>
                                <div className="d-flex justify-content-between align-items-center py-3 border-bottom">
                                    <div>
                                        <h6 className="mb-1">Email Notifications</h6>
                                        <small className="text-muted">Receive booking confirmations and updates</small>
                                    </div>
                                    <Form.Check type="switch" defaultChecked />
                                </div>
                                <div className="d-flex justify-content-between align-items-center py-3 border-bottom">
                                    <div>
                                        <h6 className="mb-1">SMS Notifications</h6>
                                        <small className="text-muted">Receive SMS alerts for bookings</small>
                                    </div>
                                    <Form.Check type="switch" defaultChecked />
                                </div>
                                <div className="d-flex justify-content-between align-items-center py-3 border-bottom">
                                    <div>
                                        <h6 className="mb-1">Marketing Emails</h6>
                                        <small className="text-muted">Receive promotional offers and updates</small>
                                    </div>
                                    <Form.Check type="switch" />
                                </div>
                                <div className="pt-3">
                                    <Button variant="danger" onClick={logout}>
                                        Logout
                                    </Button>
                                </div>
                            </Card.Body>
                        </Card>
                    )}
                </Col>
            </Row>
        </Container>
    )
}

export default UserProfilePage
