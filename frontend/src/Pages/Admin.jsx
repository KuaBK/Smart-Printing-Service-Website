import { useContext, useState, useEffect } from 'react'
import { Auth } from '../components/Auth'
import { BuyPrintingPages } from '../components/BuyPrintingPages'
import { GlobalContext } from '../Context'
import { Payment } from '../components/Payment'
import { ConfigPrint } from '../components/ConfigPrint'
import { ConfigSPSO } from '../components/ConfigSPSO'
import { HomePage } from '../components/HomePage'
import { HomePageAdmin } from '../components/HomePageAdmin'
import { HomePageUser } from '../components/HomePageUser'
import { HomePageSPSO } from '../components/HomePageSPSO'
import { Library } from '../components/Library'
import { ManagePrint } from '../components/ManagePrint'
import { Navbar } from '../components/Navbar'
import { PrintService } from '../components/PrintService'
import { Slidebar } from '../components/Sidebar'
import { StatisticsSPSO } from '../components/StatisticsSPSO'
import { Profile } from '../components/Profile'
import { Notifications } from '../components/Notifications'
import {HistoryTransaction} from '../components/HistoryTransaction'
import "react-toastify/dist/ReactToastify.css";


function getContent(type) {
  const { selecInput, setSelecInput, fetchProfile, profile, reload} = useContext(GlobalContext)
  // useEffect(() => {
  //   reload()
  // }, [])
  switch (type) {
    case 'Profile':
      return <Profile/>;
    case 'notification':
      return <Notifications/>;
    default:
      return <div className= {`flex flex-row`}>
              <Slidebar value='admin'/>
              <div className={`contentSpace`}>
                {console.log(type)}
                {type == 'HomePageAdmin' && <HomePageAdmin/>}
                {type == 'BuyPrintingPages' && <BuyPrintingPages/>}
                {type == 'HomePageUser' && <HomePageUser/>}
                {type == 'HomePageSPSO' && <HomePageSPSO/>}
                {type == 'ManagePrint' && <ManagePrint/>}
                {type == 'Library' && <Library/>}
                {type == 'StatisticsSPSO' && <StatisticsSPSO/>}
                {type == 'ConfigPrint' && <ConfigPrint/>}
                {type == 'ConfigSPSO' && <ConfigSPSO/>}
                {type == 'Payment' && <Payment/>}
                {type == 'PrintService' && <PrintService/>}
                {type == 'HistoryTransaction' && <HistoryTransaction/>}
              </div>
            </div>;
  }
}

function getUrl(type) {
  switch (type) {
    case '/user':
      return 'HomePageUser'
    case '/user/config':
      return 'PrintService'
    case '/user/lib':
      return 'Library'
    case '/user/buypages':
      return 'BuyPrintingPages'
    case '/spso':
      return 'HomePageSPSO'
    case '/spso/manageprint':
      return 'ConfigPrint'
    case '/spso/statistics':
      return 'StatisticsSPSO'
    case '/spso/config':
      return 'ConfigSPSO'
    case '/admin':
      return 'HomePageAdmin'
    default:
      return null;
  }
}

function Admin() {
  const { selecInput, setSelecInput, fetchProfile, profile, reload} = useContext(GlobalContext)
  useEffect(() => {
    // setSelecInput('HomePageUser')
    setSelecInput(getUrl(location.pathname))
    if (!profile) {
      fetchProfile()
    }
  }, [])
  useEffect(() => {
    // console.log(location.pathname)
    setSelecInput(getUrl(location.pathname))
    // if (!profile) {
    reload()
    // }
  }, [selecInput])
  return (
    <div className='my-0 p-0'>
      <Navbar/>
      {getContent(selecInput)}
    </div>
  )
}

export default Admin
