const { createApp } = Vue
createApp( {
    data(){
        return {
            name:"",
            maxAmount:"",
            payment:"",
            fees:""

        }
    },
    created(){
    },
    methods: {
        manager(){
            location.href='/manager.html'
        },
        createLoan(){
            axios.post(`/api/create/loan?maxamount=${this.maxAmount}&payment=${this.payment}&name=${this.name}&fees=${this.fees}`,{
                headers:{
                    'Content-Type': 'application/json'
                }
            }).then(()=>{
                alert("the loan was created correctly"),
                location.href="/createloan.html"
                })
            .catch(err=>console.error(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => 
                window.location.href = '/web/index.html')
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")