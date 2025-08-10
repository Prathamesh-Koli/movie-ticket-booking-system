import AdminNavbar from "./AdminNavbar"
import Sidebar from "./Sidebar"

const Layout = ({ children }) => {
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-md-2 p-0">
          <Sidebar />
        </div>
        <div className="col-md-10 p-0">
          <AdminNavbar />
          <main className="main-content p-4">{children}</main>
        </div>
      </div>
    </div>
  )
}

export default Layout
