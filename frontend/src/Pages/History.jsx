import { useContext, useState, useEffect } from 'react'
import { GlobalContext } from '../Context'
import { Navbar } from '../components/Navbar'
import { Profile } from '../components/Profile'
import { HistoryTransaction } from '../components/HistoryTransaction'

function Profile1() {
  const { selecInput, setSelecInput, fetchProfile, profile, reload, ToastContainer,toast} = useContext(GlobalContext)
  useEffect(() => {
    if (!profile) {
      fetchProfile()
    }
  }, [])
  useEffect(() => {
    // reload()
  }, [profile])
  return (
    <div className='my-0 p-0'>
      <ToastContainer/>
      <Navbar/>
      <HistoryTransaction/>
    </div>
  )
}

export default Profile1
